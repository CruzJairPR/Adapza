import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Alert } from 'reactstrap';
import { useAppSelector } from 'app/config/store';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGoogle, faFacebookF } from '@fortawesome/free-brands-svg-icons';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="home-page">
      <div className="home-content">
        <h1 className="display-4 app-title">Iniciar sesión</h1>
        {account?.login ? (
          <Alert color="success">
            You are logged in as user <strong>{account.login}</strong>.
          </Alert>
        ) : (
          <>
            <form className="login-form">
              <div className="mb-3">
                <label htmlFor="email">Correo electrónico</label>
                <input type="email" id="email" name="email" className="form-control" placeholder="Ingresa tu correo" />
              </div>
              <div className="mb-3">
                <label htmlFor="password">Contraseña</label>
                <input type="password" id="password" name="password" className="form-control" placeholder="Ingresa tu contraseña" />
              </div>
              <button type="submit" className="btn btn-primary w-100">
                Iniciar sesión
              </button>
            </form>

            <div className="mt-4">
              <button className="btn btn-google w-100 mb-2">
                <FontAwesomeIcon icon={faGoogle} className="me-2" />
                Iniciar sesión con Google
              </button>
              <button className="btn btn-facebook w-100">
                <FontAwesomeIcon icon={faFacebookF} className="me-2" />
                Iniciar sesión con Facebook
              </button>
            </div>

            <div className="text-center">
              <p className="mt-3">
                ¿No tienes una cuenta?{' '}
                <Link to="/account/register" className="alert-link">
                  Regístrate aquí
                </Link>
              </p>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default Home;
