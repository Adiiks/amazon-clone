import { Toaster } from 'react-hot-toast'
import './App.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import Registration from './components/auth/Registration';

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
      }
    ]
  }
]);

function App() {
  return (
    <>
      <RouterProvider router={router} />
      <Toaster />
    </>
  )
}

export default App
