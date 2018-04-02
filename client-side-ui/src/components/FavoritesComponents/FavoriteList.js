import React, { Component } from 'react';
import { CollectionItem, Row, Col, Icon, Button } from 'react-materialize';

class FavoriteList extends Component {


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
                            {this.props.edit ?
                                <div>
                                    <textarea
                                        name='notes'
                                        onChange={(event) => this.props.handleFavoriteChange(event, this.props.index)}
                                        onBlur={() => this.props.updateFavorite(this.props.index)}
                                        value={this.props.notes} />
                                    <Button>Save</Button>
                                </div> :
                                <div>
                                <p className='notes'>{this.props.notes}</p>
                                <Button>Edit</Button>
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

export default FavoriteList;