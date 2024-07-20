import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ResetPassword.css';
import Header from './Header';

function ResetPassword() {
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const [showAlert, setShowAlert] = useState(false);
    const navigate = useNavigate();

    const handleResetPassword = async (e) => {
    e.preventDefault();

    if (!newPassword || !confirmPassword) {
        setError('All fields are required!');
        return;
    }

    if (newPassword !== confirmPassword) {
        setError('New password and confirm password do not match');
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const email = urlParams.get('email');
    const token = urlParams.get('token');

    try {
        const response = await fetch('http://localhost:8080/api/users/reset-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, newPassword, token }),
        });

        if (response.ok) {
            setShowAlert(true);
            setTimeout(() => {
                setShowAlert(false);
                navigate('/');
            }, 2500);
        } else {
        const result = await response.text();
        setError(result);
        }
    } catch (error) {
        setError('An error occurred while resetting the password');
    }
    };

    return (
    <div className="reset-password">
        <Header />
        <div className="reset-pass-container">
        <h2>Reset Password</h2>
        {error && <div className="error-message"dangerouslySetInnerHTML={{ __html: error }}/>}
        {showAlert && (
            <div className="pass-changed-alert">
                Your password has been reset successfully!
            </div>
        )}
        <form onSubmit={handleResetPassword} className="reset-password-form">
            <input
            type="password"
            placeholder="New Password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            className="reset-password-input"
            />
            <input
            type="password"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            className="reset-password-input"
            />
            <div className="button-container">
                <button type="button" className="resend-link-button" onClick={() => navigate('/ForgotPassword')}>
                    Resend Link
                </button>
                <button type="submit" className="reset-password-button">Reset Password</button>
            </div>
        </form>
        </div>
    </div>
    );
}

export default ResetPassword;
