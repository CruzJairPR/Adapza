import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCarros } from 'app/entities/carro/carro.reducer';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { createEntity, getEntity, reset, updateEntity } from './renta.reducer';

export const RentaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carros = useAppSelector(state => state.carro.entities);
  const clientes = useAppSelector(state => state.cliente.entities);
  const rentaEntity = useAppSelector(state => state.renta.entity);
  const loading = useAppSelector(state => state.renta.loading);
  const updating = useAppSelector(state => state.renta.updating);
  const updateSuccess = useAppSelector(state => state.renta.updateSuccess);

  const handleClose = () => {
    navigate(`/renta${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCarros({}));
    dispatch(getClientes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.fechaInicio = convertDateTimeToServer(values.fechaInicio);
    values.fechaFin = convertDateTimeToServer(values.fechaFin);
    if (values.precioTotal !== undefined && typeof values.precioTotal !== 'number') {
      values.precioTotal = Number(values.precioTotal);
    }

    const entity = {
      ...rentaEntity,
      ...values,
      carro: carros.find(it => it.id.toString() === values.carro?.toString()),
      cliente: clientes.find(it => it.id.toString() === values.cliente?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaInicio: displayDefaultDateTime(),
          fechaFin: displayDefaultDateTime(),
        }
      : {
          ...rentaEntity,
          fechaInicio: convertDateTimeFromServer(rentaEntity.fechaInicio),
          fechaFin: convertDateTimeFromServer(rentaEntity.fechaFin),
          carro: rentaEntity?.carro?.id,
          cliente: rentaEntity?.cliente?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rentaCarrosApp.renta.home.createOrEditLabel" data-cy="RentaCreateUpdateHeading">
            <Translate contentKey="rentaCarrosApp.renta.home.createOrEditLabel">Create or edit a Renta</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="renta-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rentaCarrosApp.renta.fechaInicio')}
                id="renta-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.renta.fechaFin')}
                id="renta-fechaFin"
                name="fechaFin"
                data-cy="fechaFin"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rentaCarrosApp.renta.precioTotal')}
                id="renta-precioTotal"
                name="precioTotal"
                data-cy="precioTotal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.renta.estado')}
                id="renta-estado"
                name="estado"
                data-cy="estado"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="renta-carro" name="carro" data-cy="carro" label={translate('rentaCarrosApp.renta.carro')} type="select">
                <option value="" key="0" />
                {carros
                  ? carros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="renta-cliente"
                name="cliente"
                data-cy="cliente"
                label={translate('rentaCarrosApp.renta.cliente')}
                type="select"
              >
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/renta" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RentaUpdate;
