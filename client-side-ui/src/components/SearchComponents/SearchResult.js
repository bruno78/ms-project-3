import React, { Component } from 'react';
import axios from 'axios';
import {Collection, Button } from 'react-materialize';
import SearchList from './SearchList';

let count = 0;
class SearchResult extends Component {
constructor(props){
    super();    
}
    state = {
        dataApi: [],
        perPage:[],
        message: "",
    }

    retrieveData = () => {
        var url = `https://data.cityofnewyork.us/resource/24t3-xqyv.json?$q=${this.props.searchTerm.replace(/\s/g, '+')}&$limit=200&$offset=0`
        console.log('this data', url);
        try {
                fetch(url)
                    .then(results => {
                        return results.json();
                    })
                    .then(data =>{
                        let arr = [];
                        for (let key in data){
                            arr.push(data[key])
                        }
                        this.setState({dataApi: arr});
                        this.setState({perPage: arr.slice(0, 5)});

                    }).then(data => {
                        if(!data){
                            this.setState({message: "not found"})
                        }
                    })
            } catch (err) {
                console.log("Could not retrieve data from the server! " + err);
            }
    }


    componentWillReceiveProps(nextProps){
        if(this.props.search !== nextProps.search){
            this.retrieveData()
        }
    }

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

    addFavorites = async (props, index) => {
        try {
            const newFavorite = {
                location_name: props.location, 
                boro_name: props.boroname,
                type_name: props.type,
                provider: props.provider, 
                ssid: props.ssid, 
                remarks: props.remarks,
                notes: '',
                user_id: 0
            };
            // const newIdeaResponse = await axios.post(`/users/favorites`, props)
            await axios.post('http://localhost:8080/users/favorites/', newFavorite)
              .then(function (response) {
                    if(response.status === 200) {
                        console.log('Added to the database successfully!');
                    }
                    console.log(response);
              })
              .catch(function (error) {
                console.log(error);
                console.log(error.status);
              });
            console.log(newFavorite);

        } catch(error) {
            console.log('Error adding new Favorite!')
            console.log(error)
        }
    }

    render() {
        return (
            <div>
                <p>Results for wifi hotspots in: {this.props.searchTerm}</p>
                
                    {this.state.dataApi.length > 0 ?
                        <div >
                            <Button onClick={e => this.changePage(false)}>Prev</Button> <Button onClick={e => this.changePage(true)}>Next</Button>
                            <Collection>
                                {this.state.perPage.map((data, index) => {
                                    return (
                                        <SearchList {...data} key={index} addFavorites={this.addFavorites} />
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
 
export default SearchResult
