import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Alert, Col, Row } from 'reactstrap';
import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="home-page">
      <div className="home-content">
        <h1 className="display-4 app-title">Adapza Rent Car</h1>
        {account?.login ? (
          <Alert color="success">
            You are logged in as user <strong>{account.login}</strong>.
          </Alert>
        ) : (
          <>
            <Alert color="warning">
              If you want to{' '}
              <Link to="/login" className="alert-link">
                sign in
              </Link>
              , try default accounts:
              <br />- Administrator (login: admin / password: admin)
              <br />- User (login: user / password: user)
            </Alert>

            {/* <Alert color="info">
              Don't have an account?{' '}
              <Link to="/account/register" className="alert-link">
                Register here
              </Link>
            </Alert> */}
          </>
        )}
      </div>
    </div>
  );
};

export default Home;
