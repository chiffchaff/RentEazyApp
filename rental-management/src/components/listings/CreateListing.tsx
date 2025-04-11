import React, { useState } from 'react';

const CreateListing: React.FC = () => {
    const [location, setLocation] = useState('');
    const [rentAmount, setRentAmount] = useState('');
    const [amenities, setAmenities] = useState('');
    const [photos, setPhotos] = useState<File[]>([]);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Logic to handle form submission
    };

    return (
        <div>
            <h2>Create Listing</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Location:</label>
                    <input 
                        type="text" 
                        value={location} 
                        onChange={(e) => setLocation(e.target.value)} 
                        required 
                    />
                </div>
                <div>
                    <label>Rent Amount:</label>
                    <input 
                        type="number" 
                        value={rentAmount} 
                        onChange={(e) => setRentAmount(e.target.value)} 
                        required 
                    />
                </div>
                <div>
                    <label>Amenities:</label>
                    <input 
                        type="text" 
                        value={amenities} 
                        onChange={(e) => setAmenities(e.target.value)} 
                    />
                </div>
                <div>
                    <label>Photos:</label>
                    <input 
                        type="file" 
                        multiple 
                        onChange={(e) => setPhotos(Array.from(e.target.files || []))} 
                    />
                </div>
                <button type="submit">Create Listing</button>
            </form>
        </div>
    );
};

export default CreateListing;