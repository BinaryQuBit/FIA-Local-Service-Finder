import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ForgotPassword.css';
import Header from './Header';

function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [resetMessage, setResetMessage] = useState('');
  const [step, setStep] = useState(1);
  const navigate = useNavigate();

  const handleEmailSubmit = async (e) => {
    e.preventDefault();

    if (!email) {
      setError('Email is required!');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/users/resetPassword', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      });

      if (response.ok) {
        const result = await response.text();
        setResetMessage(result);
        setStep(2);
        setError('');
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError('An error occurred while sending the reset link');
    }
  };

  return (
    <div className="forgot-password">
      <Header />
      <div className="forgot-password-container">
        <h2>Forgot Password</h2>
        {error && <div className="error-message">{error}</div>}

        {step === 1 && (
          <form onSubmit={handleEmailSubmit}>
            <input
              type="email"
              placeholder="Email Address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="forgot-password-input"
            />
            <div className="forgot-password-button-container">
              <button type="button" onClick={() => navigate('/')} className="forgot-password-back-button">BACK</button>
              <button type="submit" className="forgot-password-button">Send Link</button>
            </div>
          </form>
        )}

        {step === 2 && (
          <div>
            <p>{resetMessage}</p>
            <button onClick={() => navigate('/')} className="forgot-password-button2">Go to Home</button>
          </div>
        )}
      </div>
    </div>
  );
}

export default ForgotPassword;
