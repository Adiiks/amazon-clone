import { Outlet } from "react-router";
import Header from "../components/header/Header";

const HomePage = () => {
    return (
        <>
            <Header />
            <Outlet />
        </>
    );
}

export default HomePage;