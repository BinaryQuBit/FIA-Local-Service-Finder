import React, { useContext, useEffect, useState } from "react";
import Accordion from "react-bootstrap/Accordion";
import { AuthContext } from "../AuthContext";
import DateTime from "./DateTime";
import "./Profile.css";
import SignedInHeader from "./SignedInHeader";
import { Tabs, Tab } from "react-bootstrap";
import axios from 'axios';

const statusOptions = ["Active", "In Progress", "Completed"];

function Profile() {
  const { user } = useContext(AuthContext);
  const [posts, setPosts] = useState([]);
  const [message, setMessage] = useState("");
  const [filter, setFilter] = useState("All");

  const fetchPosts = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/api/users/postservices"
      );
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      if (typeof data === "string") {
        setMessage(data);
      } else {
        setPosts(data.filter((post) => post.postedBy === user.email));
      }
    } catch (error) {
      setMessage("No Services Posted");
    }
  };

  useEffect(() => {
    fetchPosts();
  }, [user.email]);

  const handleStatusChange = async (postId, newStatus) => {
    try {
      const response = await axios.put(`http://localhost:8080/api/users/postservices/${postId}`, newStatus, { 
        headers: { 'Content-Type': 'text/plain' }, 
        withCredentials: true 
      });

      if (response.status !== 200) {
        throw new Error("Failed to update post status");
      }

      // Re-fetch the posts to update the page
      fetchPosts();
    } catch (error) {
      console.error("Error updating post status:", error);
      setMessage("Error updating post status");
    }
  };

  const handleDelete = async (postId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/postservices/${postId}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error("Failed to delete post");
      }
      fetchPosts();
      setPosts(posts.filter((post) => post.post_id !== postId));
    } catch (error) {
      console.error("Error deleting post:", error);
      setMessage("Error deleting post");
    }
  };

  const filteredPosts = filter === "All" ? posts : posts.filter((post) => post.status === filter);

  return (
    <div>
      <SignedInHeader />
      <div className="profile">
        <h1>My Posts</h1>
        <Tabs
          id="status-filter-tabs"
          activeKey={filter}
          onSelect={(k) => setFilter(k)}
          className="mb-3"
        >
          <Tab eventKey="All" title="All"></Tab>
          <Tab eventKey="Active" title="Active"></Tab>
          <Tab eventKey="In Progress" title="In Progress"></Tab>
          <Tab eventKey="Completed" title="Completed"></Tab>
        </Tabs>
        <div className="posts">
          {message ? (
            <p>{message}</p>
          ) : (
            filteredPosts.map((post) => (
              <div key={post.post_id} className="post">
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
                <div className="actionButtons">
                <div>
                  Status: {" "}
                  <select
                    value={post.status}
                    onChange={(e) => handleStatusChange(post.postId, e.target.value)}
                  >
                    {statusOptions.map((option) => (
                      <option key={option} value={option}>
                        {option}
                      </option>
                    ))}
                  </select>
                </div>
                <button onClick={() => handleDelete(post.postId)}>Delete</button>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default Profile;
