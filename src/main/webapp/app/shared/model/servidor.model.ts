import { Moment } from 'moment';
import { IDependente } from 'app/shared/model//dependente.model';
import { IParenteServidor } from 'app/shared/model//parente-servidor.model';

export interface IServidor {
    id?: number;
    matricula?: number;
    username?: string;
    nomeSocial?: string;
    datNascimento?: Moment;
    nomePai?: string;
    nomeMae?: string;
    cpf?: number;
    cpfPai?: number;
    cpfMae?: number;
    rgNum?: string;
    rgUf?: string;
    rgOrgaoExp?: string;
    rgDataExp?: Moment;
    sexo?: string;
    tipoSangue?: string;
    fatorRh?: string;
    doadorSangue?: string;
    deficienciaFisica?: string;
    deficienciaVisual?: string;
    deficienciaAuditiva?: string;
    deficienciaMental?: string;
    deficienciaIntelec?: string;
    deficReabReadaptado?: string;
    deficObservacao?: string;
    preencheCota?: string;
    emailPessoal?: string;
    emailAltern?: string;
    celular?: string;
    celularOperadora?: string;
    pasep?: string;
    ipsep?: string;
    inss?: string;
    ctpsNum?: string;
    ctpsSerie?: string;
    ctpsEmissao?: Moment;
    ctpsUf?: string;
    orgaoClasseNum?: string;
    orgaoClNome?: string;
    orgaoClUf?: string;
    orclDtaExp?: Moment;
    orclDtaValid?: Moment;
    reservista?: string;
    reservistaClasse?: string;
    numRIC?: string;
    orgaoEmissorRIC?: string;
    ufRIC?: string;
    datExpedRIC?: Moment;
    titEleitorNum?: string;
    titEleitorZona?: string;
    titEleitorSecao?: string;
    titEleitorLocal?: string;
    cnhNum?: string;
    cnhCategoria?: string;
    cnhDatValidade?: Moment;
    cnhDatExped?: Moment;
    cnhUfEmissor?: string;
    cnhDatPrimHab?: Moment;
    sassepeNum?: string;
    ufNatural?: string;
    primEmprego?: string;
    exercMagisterio?: string;
    tipoLogradouro?: number;
    descLogradouro?: string;
    nrEndereco?: string;
    complEndereco?: string;
    bairroEndereco?: string;
    cepEndereco?: number;
    ufEndereco?: string;
    telefone1?: string;
    telefone2?: string;
    descLograExt?: string;
    nrLograExt?: string;
    complLograExt?: string;
    bairroEndExt?: string;
    cidadeEndExt?: string;
    codPostalEndExt?: string;
    numRne?: string;
    orgaoEmissorRNE?: string;
    ufEmissorRNE?: string;
    datExpeRNE?: Moment;
    datChegada?: Moment;
    classTrabEstr?: number;
    casadoBR?: string;
    filhosBRr?: string;
    auxTransporte?: string;
    auxTransLinha1?: string;
    auxTransLinha2?: string;
    auxTransLinha3?: string;
    auxTransLinha4?: string;
    trabAposentado?: string;
    serMatriculas?: IDependente[];
    serMatriculas?: IParenteServidor[];
}

export class Servidor implements IServidor {
    constructor(
        public id?: number,
        public matricula?: number,
        public username?: string,
        public nomeSocial?: string,
        public datNascimento?: Moment,
        public nomePai?: string,
        public nomeMae?: string,
        public cpf?: number,
        public cpfPai?: number,
        public cpfMae?: number,
        public rgNum?: string,
        public rgUf?: string,
        public rgOrgaoExp?: string,
        public rgDataExp?: Moment,
        public sexo?: string,
        public tipoSangue?: string,
        public fatorRh?: string,
        public doadorSangue?: string,
        public deficienciaFisica?: string,
        public deficienciaVisual?: string,
        public deficienciaAuditiva?: string,
        public deficienciaMental?: string,
        public deficienciaIntelec?: string,
        public deficReabReadaptado?: string,
        public deficObservacao?: string,
        public preencheCota?: string,
        public emailPessoal?: string,
        public emailAltern?: string,
        public celular?: string,
        public celularOperadora?: string,
        public pasep?: string,
        public ipsep?: string,
        public inss?: string,
        public ctpsNum?: string,
        public ctpsSerie?: string,
        public ctpsEmissao?: Moment,
        public ctpsUf?: string,
        public orgaoClasseNum?: string,
        public orgaoClNome?: string,
        public orgaoClUf?: string,
        public orclDtaExp?: Moment,
        public orclDtaValid?: Moment,
        public reservista?: string,
        public reservistaClasse?: string,
        public numRIC?: string,
        public orgaoEmissorRIC?: string,
        public ufRIC?: string,
        public datExpedRIC?: Moment,
        public titEleitorNum?: string,
        public titEleitorZona?: string,
        public titEleitorSecao?: string,
        public titEleitorLocal?: string,
        public cnhNum?: string,
        public cnhCategoria?: string,
        public cnhDatValidade?: Moment,
        public cnhDatExped?: Moment,
        public cnhUfEmissor?: string,
        public cnhDatPrimHab?: Moment,
        public sassepeNum?: string,
        public ufNatural?: string,
        public primEmprego?: string,
        public exercMagisterio?: string,
        public tipoLogradouro?: number,
        public descLogradouro?: string,
        public nrEndereco?: string,
        public complEndereco?: string,
        public bairroEndereco?: string,
        public cepEndereco?: number,
        public ufEndereco?: string,
        public telefone1?: string,
        public telefone2?: string,
        public descLograExt?: string,
        public nrLograExt?: string,
        public complLograExt?: string,
        public bairroEndExt?: string,
        public cidadeEndExt?: string,
        public codPostalEndExt?: string,
        public numRne?: string,
        public orgaoEmissorRNE?: string,
        public ufEmissorRNE?: string,
        public datExpeRNE?: Moment,
        public datChegada?: Moment,
        public classTrabEstr?: number,
        public casadoBR?: string,
        public filhosBRr?: string,
        public auxTransporte?: string,
        public auxTransLinha1?: string,
        public auxTransLinha2?: string,
        public auxTransLinha3?: string,
        public auxTransLinha4?: string,
        public trabAposentado?: string,
        public serMatriculas?: IDependente[],
        public serMatriculas?: IParenteServidor[]
    ) {}
}
