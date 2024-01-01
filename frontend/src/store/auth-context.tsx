import axios from "axios";
import React, { createContext, useEffect, useState } from "react";

type AuthContextValue = {
    token: string | null,
    setToken: (token: string | null, expiresIn: number | undefined) => void
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

    function setTokenHandler(token: string | null, expiresIn: number | undefined) {
        if (token) {
            const expiresInDate = new Date().getTime() + expiresIn!;
            localStorage.setItem('expiresIn', expiresInDate + '');
        }

        setToken(token);
    }

    function addToken(token: string) {
        axios.defaults.headers.common["Authorization"] = "Bearer " + token;

        localStorage.setItem('token', token);

        handleLogout();
    }

    function removeToken() {
        delete axios.defaults.headers.common["Authorization"];

        localStorage.removeItem('token');
        localStorage.removeItem('expiresIn');
    }

    function handleLogout() {
        const expireAt = new Date(parseInt(localStorage.getItem('expiresIn')!));
        const dateNow = new Date();

        if (expireAt < dateNow) {
            removeToken();
        } else {
            const time = expireAt.getTime() - dateNow.getTime();
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