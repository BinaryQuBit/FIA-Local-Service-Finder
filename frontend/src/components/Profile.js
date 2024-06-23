import React from 'react';
import SignedInHeader from './SignedInHeader';

function Profile() {
    return (
        <div>
        <SignedInHeader />
        <div className="content">
            <h1>Profile</h1>
            {/* Profile content */}
        </div>
        </div>
    );
}

export default Profile;
