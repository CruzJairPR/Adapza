import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Alert } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { login } from 'app/shared/reducers/authentication';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGoogle, faFacebookF } from '@fortawesome/free-brands-svg-icons';

export const Home = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const account = useAppSelector(state => state.authentication.account);
  const loginError = useAppSelector(state => state.authentication.loginError);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  useEffect(() => {
    if (account?.login) {
      navigate('/');
    }
  }, [account, navigate]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    dispatch(login(email, password, true));
  };

  return (
    <div className="home-wrapper">
      <div className="home-container">
        <div className="home-content">
          <h1 className="app-title">Iniciar sesión</h1>
          {account?.login ? (
            <Alert color="success">
              Estás logueado como <strong>{account.login}</strong>.
            </Alert>
          ) : (
            <>
              <form className="login-form" onSubmit={handleSubmit}>
                <div className="mb-2">
                  <label htmlFor="username">Nombre de usuario</label>
                  <input
                    type="text"
                    id="username"
                    name="username"
                    className="form-control"
                    placeholder="Ingresa tu nombre de usuario"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                  />
                </div>
                <div className="mb-2">
                  <label htmlFor="password">Contraseña</label>
                  <input
                    type="password"
                    id="password"
                    name="password"
                    className="form-control"
                    placeholder="Ingresa tu contraseña"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                  />
                </div>
                {loginError && (
                  <Alert color="danger">
                    <strong>¡Error de inicio de sesión!</strong> Verifica tus credenciales y vuelve a intentarlo.
                  </Alert>
                )}
                <button type="submit" className="btn btn-primary w-100">
                  Iniciar sesión
                </button>
              </form>

              <div className="mt-3">
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
                  <Link to="/account/register" className="alert-link" style={{ color: 'yellow' }}>
                    Regístrate aquí
                  </Link>
                </p>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default Home;
