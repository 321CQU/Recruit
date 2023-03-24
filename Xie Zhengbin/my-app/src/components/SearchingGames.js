import React, { useState } from "react";
import { SortingTable } from './SortingTable'

export function SearchBox() {
  const [game, setGame] = useState("");
  const [searchData, setSearchData] = useState([]);


  const handleInputChange = (event) => {
    setGame(event.target.value);
  };

  const handleSearch = async () => {
    try {
      const response = await fetch(`http://platopeanut.top:3210/recruit/2023/getSteamInfo/${game}`);
      const data = await response.json();
      setSearchData(data.data.priceData);
    } catch (error) {
      console.error(error);
    }
  };
  

  return (
    <div>
      <input type="text" value={game} onChange={handleInputChange} />
      <button onClick={handleSearch}>Search</button>
      <br/><br/><br/><br/>
      <SortingTable searchData={searchData} />
      
    </div>
  );
}
