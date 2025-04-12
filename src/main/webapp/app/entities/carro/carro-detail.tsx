import './carro-detail.scss';
import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row, Card, CardBody } from 'reactstrap';
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
    <Row className="carro-detail">
      <Col md="6" className="info-card">
        <CardBody>
          <h2 data-cy="carroDetailsHeading">
            <Translate contentKey="rentaCarrosApp.carro.detail.title">Carro</Translate>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="global.field.id">ID</Translate>
            </dt>
            <dd>{carroEntity.id}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.marca">Marca</Translate>
            </dt>
            <dd>{carroEntity.marca}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.modelo">Modelo</Translate>
            </dt>
            <dd>{carroEntity.modelo}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.anio">Año</Translate>
            </dt>
            <dd>{carroEntity.anio}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.placas">Placas</Translate>
            </dt>
            <dd>{carroEntity.placas}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.color">Color</Translate>
            </dt>
            <dd>{carroEntity.color}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.tipo">Tipo</Translate>
            </dt>
            <dd>{carroEntity.tipo}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.estado">Estado</Translate>
            </dt>
            <dd>{carroEntity.estado}</dd>

            <dt>
              <Translate contentKey="rentaCarrosApp.carro.kilometraje">Kilometraje</Translate>
            </dt>
            <dd>{carroEntity.kilometraje}</dd>
          </dl>

          <div className="buttons-container">
            <Button tag={Link} to="/carro" replace color="info" data-cy="entityDetailsBackButton">
              <FontAwesomeIcon icon="arrow-left" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Volver</Translate>
              </span>
            </Button>
            <Button tag={Link} to={`/carro/${carroEntity.id}/edit`} replace color="primary">
              <FontAwesomeIcon icon="pencil-alt" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.edit">Editar</Translate>
              </span>
            </Button>
          </div>
        </CardBody>
      </Col>

      <Col md="6" className="carro-image">
        <img
          src="https://cdn-3.expansion.mx/dims4/default/6754fe7/2147483647/strip/true/crop/2466x1266+0+0/resize/1200x616!/format/webp/quality/60/?url=https%3A%2F%2Fcdn-3.expansion.mx%2F1a%2F90%2F23aa0bf54d42ba83af3abaecbdf4%2Fnissan-versa-2023.jpg"
          alt={`Imagen del carro ${carroEntity.modelo}`}
        />
      </Col>
    </Row>
  );
};

export default CarroDetail;
