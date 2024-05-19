import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';
import Header from './Header';
import Footer from './Footer';

function Register() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [showAlert, setShowAlert] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!email || !password || !confirmPassword) {
      setError('All fields are required!');
      return;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match!');
      return;
    }

    const accounts = JSON.parse(localStorage.getItem('accounts')) || {};

    if (accounts[email]) {
      setError('Account already exists with this email!');
      return;
    }

    accounts[email] = { password };
    localStorage.setItem('accounts', JSON.stringify(accounts));

    setShowAlert(true);

    setTimeout(() => {
      setShowAlert(false);
      navigate('/');
    }, 2000);
  };

  return (
    <div className="register">
      <Header />
      <div className="register-container">
        <h2>Register</h2>
        {error && <div className="error-message">{error}</div>}
        {showAlert && (
          <div className="custom-alert">
            Your account is successfully created!
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div>
            <input
              type="email"
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
          <div>
            <input
              type="password"
              placeholder="Confirm Password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
          </div>
          <button type="submit" className="submit-button">Register</button>
        </form>
        <button onClick={() => navigate('/')} className="back-button">BACK</button>
      </div>
      <Footer />
    </div>
  );
}

export default Register;
