import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import './Register.css';
import Header from './Header';

function Register() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [country, setCountry] = useState('');
  const [city, setCity] = useState('');
  const [province, setProvince] = useState('');
  const [postalCode, setPostalCode] = useState('');
  const [error, setError] = useState('');
  const [showAlert, setShowAlert] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (!firstName || !lastName || !email || !password || !confirmPassword || !phoneNumber || !country || !city || !province || !postalCode) {
      setError('All fields are required!');
      return;
    }
  
    if (password !== confirmPassword) {
      setError('Passwords do not match!');
      return;
    }
  
    try {
      const response = await fetch('http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password, phoneNumber, country, firstName, lastName, city, province, postalCode }),
      });
  
      if (response.ok) {
        setShowAlert(true);
        setTimeout(() => {
          setShowAlert(false);
          navigate('/');
        }, 2000);
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError('An error occurred while registering');
    }
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
          
          <div className="name-fields">
            <input
              type="text"
              placeholder="First Name"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
            />
            <input
              type="text"
              placeholder="Last Name"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
            />
          </div>
          <div className="emailAndPass">
            <input
              type="email"
              placeholder="Email Address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className="emailAndPass">
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div className="emailAndPass">
            <input
              type="password"
              placeholder="Confirm Password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
          </div>

          <div className="location-fields">
            <input
              type="country"
              placeholder="Country"
              value={country}
              onChange={(e) => setCountry(e.target.value)}
            />
            <input
              type="city"
              placeholder="City"
              value={city}
              onChange={(e) => setCity(e.target.value)}
            />
          </div>
          <div className="location-fields">
            <input
              type="province"
              placeholder="province"
              value={province}
              onChange={(e) => setProvince(e.target.value)}
            />
            <input
              type="postalCode"
              placeholder="Postal Code"
              value={postalCode}
              onChange={(e) => setPostalCode(e.target.value)}
            />
          </div>
          <div className="phone-fields">
            <PhoneInput
              country={'ca'}
              value={phoneNumber}
              onChange={setPhoneNumber}
              inputStyle={{ width: '100%' }}
            />
          </div>
          <button type="submit" className="submit-button">Register</button>
        </form>
        <button onClick={() => navigate('/')} className="back-button">BACK</button>
      </div>
    </div>
  );
}

export default Register;
