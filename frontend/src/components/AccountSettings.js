import React from 'react';
import SignedInHeader from './SignedInHeader';

function AccountSettings() {
  return (
    <div>
      <SignedInHeader />
      <div className="content">
        <h1>Account Settings</h1>
        {/* Account settings content */}
      </div>
    </div>
  );
}

export default AccountSettings;
