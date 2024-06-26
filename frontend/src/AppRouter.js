import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./AuthContext";
import ProtectedRoute from "./ProtectedRoute";
import Home from "./components/Home";
import Register from "./components/Register";
import AccountSettings from "./components/AccountSettings";
import ExploreServices from "./components/ExploreServices";
import PostServices from "./components/PostServices";
import Profile from "./components/Profile";
import Footer from "./components/Footer";
import ForgotPassword from "./components/ForgotPassword";
import "./AppRouter.css";

function AppRouter() {
  return (
    <AuthProvider>
      <Router>
        <div className="app-container">
          <div className="main-content">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/register" element={<Register />} />
              <Route path="/forgotPassword" element={<ForgotPassword />} />
              <Route element={<ProtectedRoute />}>
                <Route path="/account-settings" element={<AccountSettings />} />
                <Route path="/explore-services" element={<ExploreServices />} />
                <Route path="/post-services" element={<PostServices />} />
                <Route path="/profile" element={<Profile />} />
              </Route>
            </Routes>
          </div>
          <Footer />
        </div>
      </Router>
    </AuthProvider>
  );
}

export default AppRouter;
