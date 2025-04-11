import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './carro.reducer';

export const CarroDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const carroEntity = useAppSelector(state => state.carro.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carroDetailsHeading">
          <Translate contentKey="rentaCarrosApp.carro.detail.title">Carro</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carroEntity.id}</dd>
          <dt>
            <span id="marca">
              <Translate contentKey="rentaCarrosApp.carro.marca">Marca</Translate>
            </span>
          </dt>
          <dd>{carroEntity.marca}</dd>
          <dt>
            <span id="modelo">
              <Translate contentKey="rentaCarrosApp.carro.modelo">Modelo</Translate>
            </span>
          </dt>
          <dd>{carroEntity.modelo}</dd>
          <dt>
            <span id="anio">
              <Translate contentKey="rentaCarrosApp.carro.anio">Anio</Translate>
            </span>
          </dt>
          <dd>{carroEntity.anio}</dd>
          <dt>
            <span id="placas">
              <Translate contentKey="rentaCarrosApp.carro.placas">Placas</Translate>
            </span>
          </dt>
          <dd>{carroEntity.placas}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="rentaCarrosApp.carro.color">Color</Translate>
            </span>
          </dt>
          <dd>{carroEntity.color}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="rentaCarrosApp.carro.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{carroEntity.tipo}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="rentaCarrosApp.carro.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{carroEntity.estado}</dd>
          <dt>
            <span id="kilometraje">
              <Translate contentKey="rentaCarrosApp.carro.kilometraje">Kilometraje</Translate>
            </span>
          </dt>
          <dd>{carroEntity.kilometraje}</dd>
        </dl>
        <Button tag={Link} to="/carro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carro/${carroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarroDetail;
