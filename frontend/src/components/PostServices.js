import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import SignedInHeader from "./SignedInHeader";
import "./PostServices.css";
import { AuthContext } from "../AuthContext";

const backendUrl = process.env.REACT_APP_BACKEND_PORT;

function PostServices() {
  const { user } = useContext(AuthContext);
  const [typeService, setTypeService] = useState("");
  const [description, setDescription] = useState("");
  const [emailService, setEmailService] = useState("");
  const [phoneService, setPhoneService] = useState("");
  const [countryService, setCountryService] = useState("");
  const [cityService, setCityService] = useState("");
  const [provinceService, setProvinceService] = useState("");
  const [error, setError] = useState("");
  const [showAlert, setShowAlert] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !typeService ||
      !description ||
      !emailService ||
      !phoneService ||
      !countryService ||
      !cityService ||
      !provinceService
    ) {
      setError("All fields are required!");
      return;
    }

    try {
      const response = await fetch(
      `${backendUrl}/api/users/postservices`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            typeService,
            description,
            emailService,
            countryService,
            phoneService,
            cityService,
            provinceService,
            postedBy: user.email,
          }),
        }
      );

      if (response.ok) {
        setShowAlert(true);
        setTimeout(() => {
          setShowAlert(false);
          navigate("/profile");
        }, 2000);
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError("An error occurred while creating the post!");
    }
  };

  return (
    <div>
      <SignedInHeader />
      <div className="post-service-container">
        <h1>Post Service</h1>
        {showAlert && (
          <div className="custom-alert">Your post is successfully created!</div>
        )}
        <form onSubmit={handleSubmit} className="post-service-form">
          <div className="form-group">
            <label>Type of Services</label>
            <select
              value={typeService}
              onChange={(e) => setTypeService(e.target.value)}
            >
              <option value="">Select a service</option>
              <option value="Appliance Repair">Appliance Repair</option>
              <option value="Carpentry">Carpentry</option>
              <option value="Cleaning">Cleaning</option>
              <option value="Electrician">Electrician</option>
              <option value="Flooring Installation">
                Flooring Installation
              </option>
              <option value="Home Security Installation">
                Home Security Installation
              </option>
              <option value="HVAC Repair">HVAC Repair</option>
              <option value="Landscaping">Landscaping</option>
              <option value="Painting">Painting</option>
              <option value="Plumbing">Plumbing</option>
              <option value="Roofing">Roofing</option>
              <option value="Window Cleaning">Window Cleaning</option>
              <option value="Others">
                Others (Specify in the description)
              </option>
            </select>
          </div>
          <div className="form-group">
            <label>Description</label>
            <textarea
              placeholder="Type Here..."
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="City"
              value={cityService}
              onChange={(e) => setCityService(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Province"
              value={provinceService}
              onChange={(e) => setProvinceService(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Country"
              value={countryService}
              onChange={(e) => setCountryService(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="email"
              placeholder="Email"
              value={emailService}
              onChange={(e) => setEmailService(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Phone Number"
              value={phoneService}
              onChange={(e) => setPhoneService(e.target.value)}
            />
          </div>
          {error && <div className="error-message">{error}</div>}
          <button type="submit" className="submit-button">
            Post Service
          </button>
        </form>
      </div>
      <div></div>
    </div>
  );
}

export default PostServices;
