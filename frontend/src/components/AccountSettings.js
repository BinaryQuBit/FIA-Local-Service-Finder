import React, { useState, useContext, useEffect } from 'react';
import SignedInHeader from './SignedInHeader';
import { AuthContext } from '../AuthContext';
import axios from 'axios';
import './AccountSettings.css';

const backendUrl = process.env.REACT_APP_BACKEND_PORT;

function AccountSettings() {
  const { user } = useContext(AuthContext);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
    country: '',
    city: '',
    province: '',
    postalCode: '',
  });
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get(`${backendUrl}/api/users/userinfo`, {
          withCredentials: true,
        });

        if (response.status === 200) {
          const userData = response.data;
          setFormData({
            firstName: userData.firstName,
            lastName: userData.lastName,
            password: '',
            confirmPassword: '',
            country: userData.country,
            city: userData.city,
            province: userData.province,
            postalCode: userData.postalCode,
          });
        } else {
          throw new Error('Failed to fetch user data');
        }
      } catch (error) {
        setMessage('Error fetching user data');
      }
    };

    fetchUserData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      setMessage('Passwords do not match');
      return;
    }

    try {
      const response = await axios.put(
        `${backendUrl}/api/users/accountsettings`,
        formData,
        { withCredentials: true }
      );

      if (response.status === 200) {
        setMessage('Account settings updated successfully');
      } else {
        throw new Error('Failed to update account settings');
      }
    } catch (error) {
      setMessage('Error updating account settings');
    }
  };

  return (
    <div>
      <SignedInHeader />
      <div className="content">
        <h1>Account Settings</h1>
        {message && <p className="message">{message}</p>}
        <form onSubmit={handleSubmit} className="account-settings-form">
          <div className="form-group">
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="lastName">Last Name</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirm Password</label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="country">Country</label>
            <input
              type="text"
              id="country"
              name="country"
              value={formData.country}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="city">City</label>
            <input
              type="text"
              id="city"
              name="city"
              value={formData.city}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="province">Province</label>
            <input
              type="text"
              id="province"
              name="province"
              value={formData.province}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="postalCode">Postal Code</label>
            <input
              type="text"
              id="postalCode"
              name="postalCode"
              value={formData.postalCode}
              onChange={handleChange}
              required
            />
          </div>
          <button type="submit" className="submit-button">Update Settings</button>
        </form>
      </div>
    </div>
  );
}

export default AccountSettings;

