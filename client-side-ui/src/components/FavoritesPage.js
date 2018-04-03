import React, { Component } from 'react';
import {Button, Collection } from 'react-materialize';
import axios from 'axios';

import FavoriteItem from './FavoritesComponents/FavoriteItem';

let count = 0;
class FavoritesPage extends Component {
    state = {
        favorites: [],
        perPage:[],
        message: '',
        note:""
    }

    componentWillMount() {
        const url = `http://localhost:8080/users/favorites/`;
        
        axios.get(url)
            .then( response => {
                let arr = [];
                console.log(response.data);
                for (let key in response.data){
                    arr.push(response.data[key])
                }
                this.setState({favorites: arr});
                this.setState({perPage: arr.slice(0, 5)});

            })
            .then(data => {
                if(!data){
                    this.setState({message: "no favorites yet!"})
                }
            })
            .catch (err => {
                console.log("Could not retrieve data from the server! " + err);
                console.log(err.status);
            })
    }

    changePage = param =>{
        console.log(param)
        let currentPage;

        if (param) {
            count += 5;
        } else if (!param && count > 0){
            count -= 5;
        }

        currentPage =  this.state.favorites.slice(count, count +5); 
        this.setState({perPage: currentPage});
    }

    handleFavoriteChange = (event, index) => {
        const attributeToChange = event.target.notes
        console.log(event.target.notes);
    }

    updateFavorite = async (props) => {

        try {
            await axios({
                method: 'patch',
                url: `http://localhost:8080/users/favorites/${props.id}`,
                data: props,
                headers: { 'Access-Control-Allow-Origin': '*' }
            })
            .then( response => {
                const updatedFavorites = this.state.favorites 

            //     for(let i; updatedFavorites.length; i++) {
            //         if(updatedFavorites[i].id === props.id) {
            //             updatedFavorites[i] = props
            //         }
            //     }
                alert('Successfully updated!');
            //     this.setState({favorties: updatedFavorites});
            })
        } catch(error) {
            console.log('Error updating notes!')
            console.log(error)
        }
    }

    deleteFavorite = async (faveId, index) => {
        try {
            await axios.delete(`http://localhost:8080/users/favorites/${faveId}`)
                .then( res => {

                    if(res.data === "OK") {
                        const updatedFavoritesList = [...this.state.favorites]
                        updatedFavoritesList.splice(index, 1)
                        this.setState({favorites: updatedFavoritesList})
                    }
                    alert("Successfully deleted!")
                })     

        } catch (error) {
            console.log(`Error deleting Idea with ID of ${faveId}`)
            console.log(error)
        }
    }



    render() {
        return (
            <div className="container">

                <h2>My Favorite HotSpot Locations</h2>
            
                {this.state.favorites.length > 0 ?
                    <div >
                        <Button onClick={e => this.changePage(false)}>Prev</Button> <Button onClick={e => this.changePage(true)}>Next</Button>
                        <Collection>
                            {this.state.favorites.map((data, index) => {
                                return (
                                    <FavoriteItem {...data} key={index} 
                                        deleteFavorite={this.deleteFavorite}
                                        handleFavoriteChange={this.handleFavoriteChange}
                                        updateFavorite={this.updateFavorite}
                                        
                                        />
                                ) 
                            })}
                        </Collection>
                    </div> : 
                
                this.state.message
                    
                }    
            </div>   
        )
    }
}

export default FavoritesPage
