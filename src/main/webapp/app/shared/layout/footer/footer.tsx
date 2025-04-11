import React from 'react';
import { Col, Row } from 'reactstrap';
import './footer.scss';

// FontAwesome icons import
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faTwitter, faInstagram, faLinkedin } from '@fortawesome/free-brands-svg-icons';

const Footer = () => (
  <div className="footer">
    <Row>
      <Col md="12" className="footer-content text-center">
        <p>© 2025 Adapza Rent Car. All Rights Reserved.</p>
        <p>
          Contact us:{' '}
          <a href="mailto:contact@adapza.com" className="footer-link">
            contact@adapza.com
          </a>
        </p>
        <div className="social-links">
          <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer" className="social-link">
            <FontAwesomeIcon icon={faFacebook} /> Facebook
          </a>
          <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="social-link">
            <FontAwesomeIcon icon={faTwitter} /> Twitter
          </a>
          <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer" className="social-link">
            <FontAwesomeIcon icon={faInstagram} /> Instagram
          </a>
          <a href="https://www.linkedin.com" target="_blank" rel="noopener noreferrer" className="social-link">
            <FontAwesomeIcon icon={faLinkedin} /> LinkedIn
          </a>
        </div>
      </Col>
    </Row>
  </div>
);

export default Footer;
