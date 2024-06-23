import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Register from './components/Register';
import Welcome from './components/Welcome';
import AccountSettings from './components/AccountSettings';
import ExploreServices from './components/ExploreServices';
import PostServices from './components/PostServices';
import Profile from './components/Profile';
import Footer from './components/Footer';
import './AppRouter.css';

function AppRouter() {
  return (
    <Router>
      <div className="app-container">
        <div className="main-content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/register" element={<Register />} />
            <Route path="/welcome" element={<Welcome />} />
            <Route path="/account-settings" element={<AccountSettings />} />
            <Route path="/explore-services" element={<ExploreServices />} />
            <Route path="/post-services" element={<PostServices />} />
            <Route path="/profile" element={<Profile />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default AppRouter;
