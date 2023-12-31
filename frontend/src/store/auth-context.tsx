import axios from "axios";
import { jwtDecode } from "jwt-decode";
import React, { createContext, useEffect, useState } from "react";

type AuthContextValue = {
    token: string | null,
    setToken: (token: string | null) => void
}

type AuthProviderProps = {
    children: React.ReactNode
}

export const AuthContext = createContext<AuthContextValue>({
    token: null,
    setToken: () => { }
});

const AuthContextProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [token, setToken] = useState<string | null>(localStorage.getItem('token'));
    let logoutTimer: number | undefined;

    function setTokenHandler(token: string | null) {
        setToken(token);
    }

    function addToken(token: string) {
        axios.defaults.headers.common["Authorization"] = "Bearer " + token;

        localStorage.setItem('token', token);

        handleLogout(token);
    }

    function removeToken() {
        delete axios.defaults.headers.common["Authorization"];

        localStorage.removeItem('token');
    }

    function handleLogout(token: string) {
        const expireAt = jwtDecode(token).exp;
        const dateNow = new Date().getTime();
        const time = (expireAt! - dateNow!) * 100;

        if (time <= 0) {
            removeToken();
        } else {
            clearTimeout(logoutTimer);
            logoutTimer = setTimeout(removeToken, time);
        }
    }

    useEffect(() => {
        if (token) {
            addToken(token);

        } else {
            removeToken();
        }
    }, [token])

    const contextValue: AuthContextValue = {
        token: token,
        setToken: setTokenHandler
    }

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthContextProvider;