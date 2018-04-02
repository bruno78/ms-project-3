import React, { Component } from 'react';
import axios from 'axios';

let count = 0;
class FavoritesResults extends Component {
    constructor(props){
        super();    
    }

    state = {
        dataApi: [],
        perPage:[],
        message: "",
    }

    componentWillMount() {
        
        const url = `http://localhost:8080/users/favorites/`;

        console.log('this data', url);
        await axios.get(url)
            .then(results => {
                return results.json();
            })
            .then(data =>{
                let arr = [];
                console.log(data);
                for (let key in data){
                    arr.push(data[key])
                }
                this.setState({dataApi: arr});
                this.setState({perPage: arr.slice(0, 5)});

            })
            .then(data => {
                if(!data){
                    this.setState({message: "not found"})
                }
            })
            .catch (err => {
                console.log("Could not retrieve data from the server! " + err);
                console.log(err.status);
            })
    }
    
    
    // componentWillReceiveProps(nextProps){
    //     if(this.props.search !== nextProps.search){
    //         this.retrieveData()
    //     }
    // }

    changePage = param =>{
        console.log(param)
        let currentPage;

        if (param) {
            count += 5;
        } else if (!param && count > 0){
            count -= 5;
        }

        currentPage =  this.state.dataApi.slice(count, count +5); 
        this.setState({perPage: currentPage});
    }

    updateFavorite = () => {}
    deleteFavorite = () => {}

    render() {
        return (
            <div>
                
                {this.state.dataApi.length > 0 ?
                    <div >
                        <Button onClick={e => this.changePage(false)}>Prev</Button> <Button onClick={e => this.changePage(true)}>Next</Button>
                        <Collection>
                            {this.state.perPage.map((data, index) => {
                                return (
                                    <FavoriteList {...data} key={index} 
                                        deleteFavorite={this.deleteFavorite}
                                        updateFavorite={this.updateFavorite} />
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
 
export default FavoritesResults;