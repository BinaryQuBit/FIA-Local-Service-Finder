import React from 'react';
import { NavLink } from 'react-router-dom';
import './SignedInHeader.css';
import FiaLogo from '../Assets/FiaLogo.svg';

function SignedInHeader() {
  return (
    <header className="header">
      <div className="header-left">
        <img src={FiaLogo} alt="FiaLogo" className="logo" />
      </div>
      <div className="header-middle">
        <NavLink to="/profile" className={({ isActive }) => (isActive ? 'active-link' : '')}>Profile</NavLink>
        <NavLink to="/explore-services" className={({ isActive }) => (isActive ? 'active-link' : '')}>Explore Services</NavLink>
        <NavLink to="/post-services" className={({ isActive }) => (isActive ? 'active-link' : '')}>Post Services</NavLink>
      </div>
      <div className="header-right">
        <NavLink to="/account-settings" className={({ isActive }) => (isActive ? 'active-link' : '')}>Account Settings</NavLink>
      </div>
    </header>
  );
}

export default SignedInHeader;
