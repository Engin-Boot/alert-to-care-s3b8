import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdmitComponent } from './admit/admit.component';
import { StatusComponent } from './status/status.component';
import { DischargeComponent } from './discharge/discharge.component';


@NgModule({
  declarations: [AdmitComponent, StatusComponent, DischargeComponent],
  imports: [
    CommonModule
  ],
  exports:[AdmitComponent, StatusComponent,DischargeComponent]
})
export class PatientModule { }
