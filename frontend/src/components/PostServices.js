import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SignedInHeader from './SignedInHeader';
import './PostServices.css';

function PostServices() {
    const [typeOfService, setTypeOfService] = useState('');
    const [description, setDescription] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [country, setCountry] = useState('');
    const [city, setCity] = useState('');
    const [error, setError] = useState('');
    const [showAlert, setShowAlert] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (!typeOfService || !description || !email || !phoneNumber || !country || !city) {
            setError('All fields are required!');
            return;
        }
        
        // Submit the form data (add your form submission logic here)
    

        try {
            const response = await fetch('http://localhost:8080/api/users/postservices', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ typeOfService, description, email, country, phoneNumber, city }),
            });
        
            if (response.ok) {
                setShowAlert(true);
                setTimeout(() => {
                    setShowAlert(false);
                    navigate('/profile');
                }, 2000);
            } else {
                const result = await response.text();
                setError(result);
            }
            } catch (error) {
                setError('An error occurred while creating the post!');
            }
    };

    return (
    <div>
        <SignedInHeader />
        <div className="post-service-container">
            <h2>Post Service</h2>
            {showAlert && (
                <div className="custom-alert">
                    Your post is successfully created!
                </div>
            )}
            <form onSubmit={handleSubmit} className="post-service-form">
                <div className="form-group">
                    <label>Type of Services</label>
                    <select value={typeOfService} onChange={(e) => setTypeOfService(e.target.value)}>
                    <option value="">Select a service</option>
                    <option value="service1">Appliance Repair</option>
                    <option value="service2">Carpentry</option>
                    <option value="service3">Cleaning</option>
                    <option value="service4">Electrician</option>
                    <option value="service5">Flooring Installation</option>
                    <option value="service6">Home Security Installation</option>
                    <option value="service7">HVAC Repair</option>
                    <option value="service8">Landscaping</option>
                    <option value="service9">Painting</option>
                    <option value="service10">Plumbing</option>
                    <option value="service11">Roofing</option>
                    <option value="service12">Window Cleaning</option>
                    <option value="service13">Others (Specify in the description)</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Description</label>
                    <textarea placeholder="Type Here..." value={description} onChange={(e) => setDescription(e.target.value)} />
                </div>
                
                <div className="form-group">
                    <input type="text" placeholder="Country" value={country} onChange={(e) => setCountry(e.target.value)} />
                </div>
                <div className="form-group">
                    <input type="text" placeholder="City" value={city} onChange={(e) => setCity(e.target.value)} />
                </div>
                <div className="form-group">
                    <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
                </div>
                <div className="form-group">
                    <input type="text" placeholder="Phone Number" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
                </div>
                {error && <div className="error-message">{error}</div>}
                <button type="submit" className="submit-button">Post Service</button>
            </form>
        </div>
        <div>
        </div>
    </div>
);
}

export default PostServices;
