import { DischargeComponent } from './patient/discharge/discharge.component';
import { StatusComponent } from './patient/status/status.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home-component/home-component.component';
import { SetupComponent } from './setup-component/setup-component.component';
import {OccupancyComponent} from './occupancy-component/occupancy-component.component';
import {CareComponent} from './care-component/care-component.component';
import {AdmitComponent} from './patient/admit/admit.component';
import {CreateComponent} from './alert/create/create.component';
import {GetAlertComponent} from './alert/get-alert/get-alert.component';
import {SubscriptionComponent} from './alert/subscription/subscription.component';

const routes: Routes = [
  {path:'home',component:HomeComponent},{path:'setup',component:SetupComponent},
  {path:'occupancy',component:OccupancyComponent},{path:'care',component:CareComponent},
  {path:'occupancy/admit',component:AdmitComponent},{path:'occupancy/status', component:StatusComponent},
  {path:'occupancy/discharge', component:DischargeComponent},{path:'alert/create',component:CreateComponent},
  {path:'alert/subscribe',component:SubscriptionComponent},{path:'alert/status',component:GetAlertComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [SetupComponent,HomeComponent,OccupancyComponent,CareComponent
                                  ,AdmitComponent,StatusComponent,DischargeComponent,CreateComponent,
                                GetAlertComponent,SubscriptionComponent]