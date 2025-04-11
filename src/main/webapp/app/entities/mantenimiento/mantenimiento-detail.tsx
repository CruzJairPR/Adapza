import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mantenimiento.reducer';

export const MantenimientoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mantenimientoEntity = useAppSelector(state => state.mantenimiento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mantenimientoDetailsHeading">
          <Translate contentKey="rentaCarrosApp.mantenimiento.detail.title">Mantenimiento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mantenimientoEntity.id}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="rentaCarrosApp.mantenimiento.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{mantenimientoEntity.tipo}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="rentaCarrosApp.mantenimiento.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{mantenimientoEntity.descripcion}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="rentaCarrosApp.mantenimiento.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {mantenimientoEntity.fecha ? <TextFormat value={mantenimientoEntity.fecha} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="costo">
              <Translate contentKey="rentaCarrosApp.mantenimiento.costo">Costo</Translate>
            </span>
          </dt>
          <dd>{mantenimientoEntity.costo}</dd>
          <dt>
            <Translate contentKey="rentaCarrosApp.mantenimiento.carro">Carro</Translate>
          </dt>
          <dd>{mantenimientoEntity.carro ? mantenimientoEntity.carro.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/mantenimiento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mantenimiento/${mantenimientoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MantenimientoDetail;
