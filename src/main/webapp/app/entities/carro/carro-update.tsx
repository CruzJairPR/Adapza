import './carro-update.scss';
import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './carro.reducer';

export const CarroUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carroEntity = useAppSelector(state => state.carro.entity);
  const loading = useAppSelector(state => state.carro.loading);
  const updating = useAppSelector(state => state.carro.updating);
  const updateSuccess = useAppSelector(state => state.carro.updateSuccess);

  const handleClose = () => {
    navigate(`/carro${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.anio !== undefined && typeof values.anio !== 'number') {
      values.anio = Number(values.anio);
    }
    if (values.kilometraje !== undefined && typeof values.kilometraje !== 'number') {
      values.kilometraje = Number(values.kilometraje);
    }

    const entity = {
      ...carroEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...carroEntity,
        };

  return (
    <div className="carro-update-container">
      <Row className="justify-content-center">
        <Col md="8">
          <h2 className="carro-update-title" id="rentaCarrosApp.carro.home.createOrEditLabel" data-cy="CarroCreateUpdateHeading">
            <Translate contentKey="rentaCarrosApp.carro.home.createOrEditLabel">Create or edit a Carro</Translate>
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
                  id="carro-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rentaCarrosApp.carro.marca')}
                id="carro-marca"
                name="marca"
                data-cy="marca"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.carro.modelo')}
                id="carro-modelo"
                name="modelo"
                data-cy="modelo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.carro.anio')}
                id="carro-anio"
                name="anio"
                data-cy="anio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.carro.placas')}
                id="carro-placas"
                name="placas"
                data-cy="placas"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('rentaCarrosApp.carro.color')} id="carro-color" name="color" data-cy="color" type="text" />
              <ValidatedField label={translate('rentaCarrosApp.carro.tipo')} id="carro-tipo" name="tipo" data-cy="tipo" type="text" />
              <ValidatedField
                label={translate('rentaCarrosApp.carro.estado')}
                id="carro-estado"
                name="estado"
                data-cy="estado"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.carro.kilometraje')}
                id="carro-kilometraje"
                name="kilometraje"
                data-cy="kilometraje"
                type="text"
              />
              <div className="d-flex justify-content-center mt-4">
                <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carro" replace color="info" className="me-2">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </div>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CarroUpdate;
