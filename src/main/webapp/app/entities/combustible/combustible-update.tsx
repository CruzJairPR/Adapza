import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCarros } from 'app/entities/carro/carro.reducer';
import { createEntity, getEntity, updateEntity } from './combustible.reducer';

export const CombustibleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carros = useAppSelector(state => state.carro.entities);
  const combustibleEntity = useAppSelector(state => state.combustible.entity);
  const loading = useAppSelector(state => state.combustible.loading);
  const updating = useAppSelector(state => state.combustible.updating);
  const updateSuccess = useAppSelector(state => state.combustible.updateSuccess);

  const handleClose = () => {
    navigate('/combustible');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCarros({}));
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
    if (values.nivelActual !== undefined && typeof values.nivelActual !== 'number') {
      values.nivelActual = Number(values.nivelActual);
    }
    values.fechaRegistro = convertDateTimeToServer(values.fechaRegistro);

    const entity = {
      ...combustibleEntity,
      ...values,
      carro: carros.find(it => it.id.toString() === values.carro?.toString()),
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
          fechaRegistro: displayDefaultDateTime(),
        }
      : {
          ...combustibleEntity,
          fechaRegistro: convertDateTimeFromServer(combustibleEntity.fechaRegistro),
          carro: combustibleEntity?.carro?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rentaCarrosApp.combustible.home.createOrEditLabel" data-cy="CombustibleCreateUpdateHeading">
            <Translate contentKey="rentaCarrosApp.combustible.home.createOrEditLabel">Create or edit a Combustible</Translate>
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
                  id="combustible-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rentaCarrosApp.combustible.nivelActual')}
                id="combustible-nivelActual"
                name="nivelActual"
                data-cy="nivelActual"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rentaCarrosApp.combustible.tipo')}
                id="combustible-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
              />
              <ValidatedField
                label={translate('rentaCarrosApp.combustible.fechaRegistro')}
                id="combustible-fechaRegistro"
                name="fechaRegistro"
                data-cy="fechaRegistro"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="combustible-carro"
                name="carro"
                data-cy="carro"
                label={translate('rentaCarrosApp.combustible.carro')}
                type="select"
              >
                <option value="" key="0" />
                {carros
                  ? carros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/combustible" replace color="info">
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

export default CombustibleUpdate;
