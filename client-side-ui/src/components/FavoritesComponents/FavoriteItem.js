import React, { Component } from 'react';
import { CollectionItem, Row, Col, Icon, Button } from 'react-materialize';

class FavoriteItem extends Component {
    state = {
        edit: false,
        // currenItem: this.props
        newFavorite: this.props,
    }

    toggleEdit(){
        this.setState({
            edit: !this.state.edit
        })
    }

    handleChange = (event) => {
        const attributeToChange = event.target.name
        const newValue = event.target.value

        const updateNewFavorite = {...this.state.newFavorite}
        updateNewFavorite[attributeToChange] = newValue
        console.log("attribute to change ", attributeToChange)
        console.log("new value ", newValue)
        this.setState({ newFavorite: updateNewFavorite })
    }

    handleSubmit = (event) => {
        event.preventDefault();

        this.props.updateFavorite(this.state.newFavorite);
        console.log("Inside form: " + Object.entries(this.state.newFavorite));
    }

    render(){
        return (
            <CollectionItem>
                <div className='record-list'>
                    <Row className='record-title'>
                        <Col s={10}>
                            <h5>
                                {this.props.location_name}
                            </h5>
                        </Col>
                        <Col s={2} className='delete-button text-right'>
                            <Button onClick={() => this.props.deleteFavorite(this.props.id, this.props.index)}>Delete</Button>
                        </Col>
                    </Row>
                    <hr/>
                    <Row>
                        <Col s={6}>
                            <small><strong>{this.props.boro_name}</strong></small>
                        </Col>
                        <Col s={6} className='text-right'>
                            <small>
                                <Icon>local_offer</Icon> {this.props.type_name} &nbsp;&nbsp;
                            </small>
                        </Col>
                    </Row>
                    <Row>
                        <Col s={12}>
                            <p className='short-description'>Remarks: {this.props.remarks}</p>
                        </Col>
                        <Col s={12}>
                            <p>Notes: </p>
                            {this.state.edit ?
                                <div>
                                    <form onSubmit={this.handleSubmit} >
                                        <input
                                            name="notes"
                                            defaultValue={this.props.notes}
                                            onChange={this.handleChange} />
                                        <div>
                                            <input type="hidden" id="id" name="id" value={this.props.id} />
                                        </div>
                                        <input type="submit" value="Save" />
                                    </form>
                                </div> :
                                <div>
                                <p className='notes'>{this.props.notes}</p>
                                <Button onClick={() => this.toggleEdit()}>Edit</Button>
                            </div>
                            }
                            
                        </Col>
                        <Col s={4}>
                            <p>Provider: {this.props.provider}</p>
                        </Col>
                        <Col s={4}>
                            <p>Network name: {this.props.ssid}</p>
                        </Col>
                        <Col s={4}>
                            <p>Type: {this.props.type_name}</p>
                        </Col>
                    </Row>
                </div>
            </CollectionItem>
        )
    }
}

export default FavoriteItem;