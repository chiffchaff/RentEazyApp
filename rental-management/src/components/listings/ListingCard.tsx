export const ListingCard = ({ listing }) => {
    return (
        <div className="listing-card">
            <h2>{listing.title}</h2>
            <p>Location: {listing.location}</p>
            <p>Rent: ${listing.rent}</p>
            <p>Amenities: {listing.amenities.join(', ')}</p>
            <img src={listing.image} alt={listing.title} />
        </div>
    );
};