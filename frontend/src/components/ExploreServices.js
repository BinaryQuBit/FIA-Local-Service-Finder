import React, { useState, useEffect } from 'react';
import Accordion from 'react-bootstrap/Accordion';
import SignedInHeader from './SignedInHeader';
import DateTime from './DateTime';
import './ExploreServices.css';

function ExploreServices() {
  const [posts, setPosts] = useState([]);
  const [message, setMessage] = useState('');
  const [searchTerm, setSearchTerm] = useState('');

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
          setPosts(data.filter(post => post.status === 'active'));
        }
      } catch (error) {
        console.error('Error fetching posts:', error);
        setMessage('Error fetching posts');
      }
    };

    fetchPosts();
  }, []);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const filteredPosts = posts.filter(post =>
    post.typeService.toLowerCase().includes(searchTerm.toLowerCase()) ||
    post.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
    post.countryService.toLowerCase().includes(searchTerm.toLowerCase()) ||
    post.cityService.toLowerCase().includes(searchTerm.toLowerCase()) ||
    post.provinceService.toLowerCase().includes(searchTerm.toLowerCase()) ||
    post.emailService.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <SignedInHeader />
      <div className="explorePage">
        <h1>Explore Services</h1>
        <input
          type="text"
          placeholder="Search services..."
          value={searchTerm}
          onChange={handleSearch}
          className="search-bar"
        />
        <div className="posts">
          {message ? (
            <p>{message}</p>
          ) : (
            filteredPosts.map((post, index) => (
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

export default ExploreServices;
