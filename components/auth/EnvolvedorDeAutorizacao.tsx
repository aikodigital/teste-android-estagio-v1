import useRodeEmIntervalo from '@/custom-hooks/useRodeEmIntervalo';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';
import React, { PropsWithChildren } from 'react';

const EnvolvedorDeAutorizacao = ({ children }: PropsWithChildren) => {
  useRodeEmIntervalo(autenticarNaApi, 600000);
  return <>{children}</>;
};

export default EnvolvedorDeAutorizacao;
