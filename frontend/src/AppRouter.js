import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Register from './components/Register';
import Welcome from './components/Welcome';
import { AuthProvider } from './AuthContext';
import ProtectedRoute from './ProtectedRoute';

function AppRouter() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route element={<ProtectedRoute />}>
            <Route path="/welcome" element={<Welcome />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default AppRouter;

