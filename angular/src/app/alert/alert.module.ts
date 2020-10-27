import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateComponent } from './create/create.component';
import { SubscriptionComponent } from './subscription/subscription.component';
import { GetAlertComponent } from './get-alert/get-alert.component';



@NgModule({
  declarations: [CreateComponent, SubscriptionComponent, GetAlertComponent],
  imports: [
    CommonModule
  ],
  exports:[CreateComponent, SubscriptionComponent,GetAlertComponent]
})
export class AlertModule { }
