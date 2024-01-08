import { Toaster } from 'react-hot-toast'
import './App.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import Registration from './components/auth/Registration';
import Login from './components/auth/Login';
import AuthContextProvider from './store/auth-context';
import MyAccount from './components/my-account/MyAccount';
import ProtectedRoute from './utils/ProtectedRoute';
import ProductCreation from './components/product-creation/ProductCreation';
import ProductDetails from './components/product-details/ProductDetails';
import CategoriesContextProvider from './store/categories-context';
import HomeContainer from './components/home/HomeContainer';

const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
    children: [
      {
        path: 'my-account',
        element:
          <ProtectedRoute>
            <MyAccount />
          </ProtectedRoute>
      },
      {
        path: 'post-product',
        element:
          <ProtectedRoute>
            <ProductCreation />
          </ProtectedRoute>
      },
      {
        path: 'product-details/:productId',
        element: <ProductDetails />
      },
      {
        path: '',
        element: <HomeContainer />
      }
    ]
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
        <CategoriesContextProvider>
          <RouterProvider router={router} />
        </CategoriesContextProvider>
      </AuthContextProvider>
      <Toaster />
    </>
  )
}

export default App
