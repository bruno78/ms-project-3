import React, { Component } from 'react';
import SearchBar from './SearchComponents/SearchBar';
import SearchResult from './SearchComponents/SearchResult';

class SearchPage extends Component {
    constructor(props) {
        super(props);
        this.state = {searchTerm: '', search: false};
        this.handleSearchTermSubmit = this.handleSearchTermSubmit.bind(this);
        this.handleSearchTermChange = this.handleSearchTermChange.bind(this);
      }
      
      handleSearchTermChange(searchTerm) {
        this.setState({searchTerm, search: false});
      }
      
      handleSearchTermSubmit() {
        this.setState({search: true});
      }
      
      render() {
        return (
          <div className="container">
            <SearchBar
              searchTerm={this.state.searchTerm}
              onSearchTermChange={this.handleSearchTermChange}
              onSearchTermSubmit={this.handleSearchTermSubmit} />
            <SearchResult searchTerm={this.state.searchTerm} search={this.state.search} />
          </div>
        );
      }
}
 
export default SearchPage;