import React, { useState, useContext, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Home.css';
import Header from './Header';
import Footer from './Footer';
import { AuthContext } from '../AuthContext';

function Home() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError('All fields are required!');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/api/users/login', { email, password }, { withCredentials: true });
      if (response.status === 200) {
        login(email);
        navigate('/welcome');
      }
    } catch (error) {
      setError('Invalid email or password');
    }
  };

  useEffect(() => {
    const elements = document.querySelectorAll('.home-left, .home-right, .home-info, .home-steps, .home-info-image, .home-steps-image');
  
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add('in-view');
          observer.unobserve(entry.target);
        }
      });
    });
  
    elements.forEach(element => {
      observer.observe(element);
    });
  
    return () => {
      elements.forEach(element => {
        observer.unobserve(element);
      });
    };
  }, []);

  return (
    <div className="home-container">
      <Header />
      <div className="home-content">
        <div className="home-left">
          <h1 className="home-title">FIA</h1>
          <p className="home-subtitle">(Find It All)</p>
          <p className="home-subtitle">Local businesses often struggle to grow due to 
          high competition and lack of effective SEO. FIA helps local businesses improve 
          their online visibility and attract more customers by providing a platform for 
          users to both list and request services</p>
        </div>
        <div className="home-right">
          <form className="home-form" onSubmit={handleSubmit}>
            {error && <div className="error-message">{error}</div>}
            <div>
              <input
                type="text"
                placeholder="Email address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div>
              <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            <div className="btn-and-links">
                <div>
                <button type="submit" className="home-button">Log in</button>
                </div>
                <div className="home-links">
                <Link to="#" className="forgot-link">Forgotten password?</Link>
                </div>
                <div className="register-button">
                <Link to="/register" className="home-register-button">Create new account</Link>
                </div>
            </div>
          </form>
        </div>
      </div>
      <div className="home-extra-content">
        <div className="home-section">
          <img src="/why.png" alt="Together" className="home-info-image" />
          <div className="home-info">
            <h2>Why Choose FIA?</h2>
            <p>
              At FIA, we aim to bridge the gap between service providers and seekers. Whether you are offering services or in need of one, our platform provides an easy-to-use interface to connect you with the right people.
            </p>
            <h3>Features:</h3>
            <ul>
              <li>Post services you offer.</li>
              <li>Request services you need.</li>
              <li>Search and browse through various service categories.</li>
              <li>Contact service providers directly for more details.</li>
              <li>Improve your business visibility with effective listings.</li>
            </ul>
          </div>
        </div>
        <div className="home-section">
          <div className="home-steps">
            <h2>How It Works</h2>
            <ol>
              <li>Sign up for an account or log in if you already have one.</li>
              <li>Post a service you offer or request a service you need.</li>
              <li>Browse through available services and contact providers or requesters.</li>
              <li>Engage with the community and grow your business.</li>
            </ol>
          </div>
          <img src="/how.png" alt="Join us" className="home-steps-image" />
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Home;
