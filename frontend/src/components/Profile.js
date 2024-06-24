import React, { useContext, useEffect, useState } from 'react';
import Accordion from 'react-bootstrap/Accordion';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../AuthContext';
import DateTime from './DateTime';
import './Profile.css';
import SignedInHeader from './SignedInHeader';

function Profile() {
    const navigate = useNavigate();
    const { user, logout } = useContext(AuthContext);
    const [posts, setPosts] = useState([]);
    const [message, setMessage] = useState('');

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/users/postservices');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                if (typeof data === 'string') {
                    setMessage(data);
                } else {
                    setPosts(data.filter(post => post.postedBy === user.email));
                }
            } catch (error) {
                console.error('Error fetching posts:', error);
                setMessage('Error fetching posts');
            }
        };
    
        fetchPosts();
    }, [user.email]);


    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <div>
        <SignedInHeader />
        <div className="profile">
            <h1>My Posts</h1>
            <button onClick={handleLogout} className="logout-button">Logout</button>
            <div className="posts">
            {message ? (
                <p>{message}</p>
            ) : (
                posts.map((post, index) => (
                <div key={index} className="post">
                    <h2>{post.typeService}</h2>
                    <DateTime timestamp={post.timestamp} />
                    <Accordion defaultActiveKey="0">
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>Description</Accordion.Header>
                        <Accordion.Body>{post.description}</Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1">
                        <Accordion.Header>Location and Contact Info</Accordion.Header>
                        <Accordion.Body>
                        <p><strong>Email:</strong> {post.emailService}</p>
                        <p><strong>Phone:</strong> {post.phoneService}</p>
                        <p><strong>Country:</strong> {post.countryService}</p>
                        <p><strong>Province:</strong> {post.provinceService}</p>
                        <p><strong>City:</strong> {post.cityService}</p>
                        </Accordion.Body>
                    </Accordion.Item>
                    </Accordion>
                    <p>Status: {post.status}</p>
                </div>
                ))
            )}
            </div>
        </div>
        </div>
    );
}

export default Profile;
