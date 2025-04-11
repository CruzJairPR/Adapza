import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './renta.reducer';

export const RentaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rentaEntity = useAppSelector(state => state.renta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rentaDetailsHeading">
          <Translate contentKey="rentaCarrosApp.renta.detail.title">Renta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rentaEntity.id}</dd>
          <dt>
            <span id="fechaInicio">
              <Translate contentKey="rentaCarrosApp.renta.fechaInicio">Fecha Inicio</Translate>
            </span>
          </dt>
          <dd>{rentaEntity.fechaInicio ? <TextFormat value={rentaEntity.fechaInicio} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fechaFin">
              <Translate contentKey="rentaCarrosApp.renta.fechaFin">Fecha Fin</Translate>
            </span>
          </dt>
          <dd>{rentaEntity.fechaFin ? <TextFormat value={rentaEntity.fechaFin} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="precioTotal">
              <Translate contentKey="rentaCarrosApp.renta.precioTotal">Precio Total</Translate>
            </span>
          </dt>
          <dd>{rentaEntity.precioTotal}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="rentaCarrosApp.renta.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{rentaEntity.estado}</dd>
          <dt>
            <Translate contentKey="rentaCarrosApp.renta.carro">Carro</Translate>
          </dt>
          <dd>{rentaEntity.carro ? rentaEntity.carro.id : ''}</dd>
          <dt>
            <Translate contentKey="rentaCarrosApp.renta.cliente">Cliente</Translate>
          </dt>
          <dd>{rentaEntity.cliente ? rentaEntity.cliente.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/renta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/renta/${rentaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RentaDetail;
