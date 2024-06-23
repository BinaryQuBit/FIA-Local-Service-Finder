import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const email = sessionStorage.getItem('userEmail');
    if (email) {
      console.log('User is already logged in with email:', email);
      setUser({ email });
    }
    setLoading(false);
  }, []);

  useEffect(() => {
    console.log('This is user:', user);
  }, [user]);

  const login = (email) => {
    console.log('Logging in with email:', email);
    setUser({ email });
    sessionStorage.setItem('userEmail', email);
  };

  const logout = () => {
    console.log('Logging out user with email:', user?.email);
    setUser(null);
    sessionStorage.removeItem('userEmail');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

