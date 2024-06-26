import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ForgotPassword.css';
import Header from './Header';

function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [code, setCode] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [step, setStep] = useState(1);
  const navigate = useNavigate();

  const handleEmailSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/users/forgot-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      });

      if (response.ok) {
        setStep(2);
        setError('');
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError('An error occurred while sending the reset code');
    }
  };

  const handleCodeSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/users/verify-reset-code', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, resetCode: code }),
      });

      if (response.ok) {
        setStep(3);
        setError('');
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError('An error occurred while verifying the reset code');
    }
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();

    if (newPassword !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/users/reset-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, newPassword, confirmPassword }),
      });

      if (response.ok) {
        navigate('/');
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError('An error occurred while resetting the password');
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
            />
            <button type="submit">Send Code</button>
          </form>
        )}

        {step === 2 && (
          <form onSubmit={handleCodeSubmit}>
            <input
              type="text"
              placeholder="Enter Code"
              value={code}
              onChange={(e) => setCode(e.target.value)}
            />
            <button type="submit">Verify Code</button>
          </form>
        )}

        {step === 3 && (
          <form onSubmit={handlePasswordSubmit}>
            <input
              type="password"
              placeholder="New Password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
            />
            <input
              type="password"
              placeholder="Confirm Password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <button type="submit">Reset Password</button>
          </form>
        )}
      </div>
    </div>
  );
}

export default ForgotPassword;
