import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './combustible.reducer';

export const CombustibleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const combustibleEntity = useAppSelector(state => state.combustible.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="combustibleDetailsHeading">
          <Translate contentKey="rentaCarrosApp.combustible.detail.title">Combustible</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{combustibleEntity.id}</dd>
          <dt>
            <span id="nivelActual">
              <Translate contentKey="rentaCarrosApp.combustible.nivelActual">Nivel Actual</Translate>
            </span>
          </dt>
          <dd>{combustibleEntity.nivelActual}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="rentaCarrosApp.combustible.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{combustibleEntity.tipo}</dd>
          <dt>
            <span id="fechaRegistro">
              <Translate contentKey="rentaCarrosApp.combustible.fechaRegistro">Fecha Registro</Translate>
            </span>
          </dt>
          <dd>
            {combustibleEntity.fechaRegistro ? (
              <TextFormat value={combustibleEntity.fechaRegistro} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="rentaCarrosApp.combustible.carro">Carro</Translate>
          </dt>
          <dd>{combustibleEntity.carro ? combustibleEntity.carro.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/combustible" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/combustible/${combustibleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CombustibleDetail;
