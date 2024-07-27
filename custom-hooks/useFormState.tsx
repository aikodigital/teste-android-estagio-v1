import { useState } from 'react';

const useFormState = () => {
  const [formState, setFormState] = useState({ loading: false, error: '' });
  return { formState, setFormState };
};

export default useFormState;
