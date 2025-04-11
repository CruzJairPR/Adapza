import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ubicacion.reducer';

export const UbicacionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ubicacionEntity = useAppSelector(state => state.ubicacion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ubicacionDetailsHeading">
          <Translate contentKey="rentaCarrosApp.ubicacion.detail.title">Ubicacion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ubicacionEntity.id}</dd>
          <dt>
            <span id="latitud">
              <Translate contentKey="rentaCarrosApp.ubicacion.latitud">Latitud</Translate>
            </span>
          </dt>
          <dd>{ubicacionEntity.latitud}</dd>
          <dt>
            <span id="longitud">
              <Translate contentKey="rentaCarrosApp.ubicacion.longitud">Longitud</Translate>
            </span>
          </dt>
          <dd>{ubicacionEntity.longitud}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="rentaCarrosApp.ubicacion.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {ubicacionEntity.timestamp ? <TextFormat value={ubicacionEntity.timestamp} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="rentaCarrosApp.ubicacion.carro">Carro</Translate>
          </dt>
          <dd>{ubicacionEntity.carro ? ubicacionEntity.carro.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ubicacion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ubicacion/${ubicacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UbicacionDetail;
