import { AbstractControl, ValidatorFn } from '@angular/forms';

export function positiveNumberValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;

    if (value === null || value === undefined || value === '') {
      return null;
    }

    const numberValue = parseFloat(value);
    if (isNaN(numberValue) || numberValue <= 0) {
      return { 'positive': true };
    }

    return null;
  };
}
