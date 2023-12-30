import { Toaster } from 'react-hot-toast'
import './App.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import Registration from './components/auth/Registration';
import Login from './components/auth/Login';
import AuthContextProvider from './store/auth-context';

const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />
  },
  {
    path: '/auth',
    element: <AuthPage />,
    children: [
      {
        path: 'sign-up',
        element: <Registration />
      },
      {
        path: 'sign-in',
        element: <Login />
      }
    ]
  }
]);

function App() {
  return (
    <>
      <AuthContextProvider>
        <RouterProvider router={router} />
      </AuthContextProvider>
      <Toaster />
    </>
  )
}

export default App
