import React, { useContext, useEffect, useState } from "react";
import { AuthContext } from "../AuthContext";
import DateTime from "./DateTime";
import "./Profile.css";
import SignedInHeader from "./SignedInHeader";
import axios from 'axios';

const statusOptions = ["Active", "In Progress", "Completed", "All"];
const backendUrl = process.env.REACT_APP_BACKEND_PORT;

function Profile() {
  const { user } = useContext(AuthContext);
  const [posts, setPosts] = useState([]);
  const [message, setMessage] = useState("");
  const [filter, setFilter] = useState("All");

  const fetchPosts = async () => {
    try {
      const response = await fetch(
        `${backendUrl}/api/users/postservices`
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
      const response = await axios.put(`${backendUrl}/api/users/postservices/${postId}`, newStatus, { 
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
      const response = await fetch(`${backendUrl}/api/users/postservices/${postId}`, {
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
        <div className="tabs">
          {statusOptions.map((option) => (
            <div
              key={option}
              className={`tab ${filter === option ? 'active' : ''}`}
              onClick={() => setFilter(option)}
            >
              {option}
            </div>
          ))}
        </div>
        <div className="posts">
          {message ? (
            <p>{message}</p>
          ) : (
            filteredPosts.map((post) => (
              <div key={post.post_id} className="post">
                <h2>{post.typeService}</h2>
                <DateTime timestamp={post.timestamp} />
                <div className="accordion">
                  <div className="accordion-item">
                    <div className="accordion-header">Description</div>
                    <div className="accordion-body">{post.description}</div>
                  </div>
                  <div className="accordion-item">
                    <div className="accordion-header">Location and Contact Info</div>
                    <div className="accordion-body">
                      <p><strong>Email:</strong> {post.emailService}</p>
                      <p><strong>Phone:</strong> {post.phoneService}</p>
                      <p><strong>Country:</strong> {post.countryService}</p>
                      <p><strong>Province:</strong> {post.provinceService}</p>
                      <p><strong>City:</strong> {post.cityService}</p>
                    </div>
                  </div>
                </div>
                <div className="action-buttons">
                  <div>
                    Status: 
                    <select
                      value={post.status}
                      onChange={(e) => handleStatusChange(post.postId, e.target.value)}
                    >
                      {statusOptions.slice(0, 3).map((option) => (
                        <option key={option} value={option}>
                          {option}
                        </option>
                      ))}
                    </select>
                  </div>
                  <button className="delete-button" onClick={() => handleDelete(post.postId)}>Delete</button>
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
