import React, { useContext } from "react";
import { AuthContext } from "../store/auth-context";
import { Navigate } from "react-router";

type Props = {
    children: React.ReactNode
}

const ProtectedRoute: React.FC<Props> = ({ children }) => {
    const authContext = useContext(AuthContext);

    if (!authContext.token) {
        return <Navigate to='/auth/sign-in' replace />
    }

    return children;
}

export default ProtectedRoute;